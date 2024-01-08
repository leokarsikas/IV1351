/*
 * The MIT License (MIT)
 * Copyright (c) 2020 Leif Lindbäck
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction,including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so,subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package se.kth.iv1351.rentaljdbc.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import se.kth.iv1351.rentaljdbc.model.Instrument;
import se.kth.iv1351.rentaljdbc.model.Rental;

import java.sql.Timestamp;

/**
 * This data access object (DAO) encapsulates all database calls in the rental
 * application. No code outside this class shall have any knowledge about the
 * database.
 */
public class RentalDAO {

    private static final String INSTRUMENTS_TABLE_NAME = "instrument";
    private static final String INSTRUMENT_ID_COLUMN_NAME = "instrument_id";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "type";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_RENTING_FEE_COLUMN_NAME = "renting_fee";

    private static final String RENTAL_TABLE_NAME = "rental";
    private static final String RENTAL_INSTRUMENT_ID_COLUMN_NAME = "instrument_id";
    private static final String RENTAL_STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String RENTAL_START_DATE_COLUMN_NAME = "start_date";
    private static final String RENTAL_END_DATE_COLUMN_NAME = "end_date";
    private static final String RENTAL_TERMINATED_RENTAL_COLUMN_NAME = "terminated_rental";

    private Connection connection;

    private PreparedStatement findAllRentableInstrumentsStmt;
    private PreparedStatement checkAmountOfStudentRentalsStmt;
    private PreparedStatement checkInstrumentAvailabilityStmt;
    private PreparedStatement addRentalStmt;
    private PreparedStatement checkTerminationStmt;
    private PreparedStatement terminateRentalStmt;

    /**
     * Constructs a new DAO object connected to the bank database.
     */
    public RentalDAO() throws RentalDBException {
        try {
            connectToBankDB();
            prepareStatements();
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new RentalDBException("Could not connect to datasource.", exception);
        }
    }

    /**
     * Finds all instruments.
     * 
     * @return The instruments.
     * @throws RentalDBException If failed at getting instruments from the DB.
     */
    public List<Instrument> findInstruments(String type) throws RentalDBException {
        String failureMsg = "Could not list instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try {
            findAllRentableInstrumentsStmt.setString(1, type);
            ResultSet result = findAllRentableInstrumentsStmt.executeQuery();
            while (result.next()) {
                instruments.add(new Instrument(result.getInt(INSTRUMENT_ID_COLUMN_NAME),
                    result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                    result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                    result.getInt(INSTRUMENT_RENTING_FEE_COLUMN_NAME)));
            }
            connection.commit();
        }
        catch (SQLException sqle) {
            handleException(failureMsg, sqle);
        }
        return instruments;
    }

    /**
     * Rents an instrument.
     * 
     * @param instrumentID The ID of the instrument.
     * @param studentID The ID of the student.
     * @param startDate The start date of the rental.
     * @param endDate The end date of the rental.
     * @return Information of the rental.
     * @throws RentalDBException If it fails at getting or setting information about the instrument in the DB.
     */
    public Rental rentInstrument(Integer instrumentID, Integer studentID, Timestamp startDate, Timestamp endDate) throws RentalDBException {
        String failureMsg = "Could not create rental.";
        if(instrumentID == null || studentID == null || startDate == null || endDate == null){
            throw new RentalDBException(failureMsg);
        }
    
        try{
            addRentalStmt.setInt(1, instrumentID);
            addRentalStmt.setInt(2, studentID);
            addRentalStmt.setTimestamp(3, startDate);
            addRentalStmt.setTimestamp(4, endDate);
            int updatedRows = addRentalStmt.executeUpdate();
            if (updatedRows != 1) {
                throw new RentalDBException(failureMsg);
            }
            return new Rental(instrumentID, studentID, startDate, endDate);
        }
        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        return null;
    }

    /**
     * Checks if an instrument is rentable.
     * 
     * @param instrumentID The ID of the instrument.
     * @return A boolean about whether or not the instrument is rentable.
     * @throws RentalDBException If it fails at checking if the instrument is rentable.
     */
    public Boolean isInstrumentRentable(Integer instrumentID) throws RentalDBException{
        String failureMsg = "Could not check if the instrument is rentable";

        if(instrumentID == null){
            throw new RentalDBException(failureMsg);
        }

        try{
            checkInstrumentAvailabilityStmt.setInt(1, instrumentID);
            ResultSet availabilityResult = checkInstrumentAvailabilityStmt.executeQuery();

            if (availabilityResult.next()) {
                return availabilityResult.getBoolean("is_instrument_available");
            }
            else{
                throw new RentalDBException(failureMsg);
            }
        }
        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        return null;
    }

    /**
     * Checks if a student is allowed to rent an instrument.
     * 
     * @param studentID The ID of the student.
     * @return A boolean about whether or not they are allowed to rent.
     * @throws RentalDBException If it fails at checking whether the student is allowed to rent or now.
     */
    public Boolean isStudentAllowedToRent(Integer studentID) throws RentalDBException{
        String failureMsg = "Could not check if the student is allowed to rent";

        if(studentID == null){
            throw new RentalDBException(failureMsg);
        }

        try{
            checkAmountOfStudentRentalsStmt.setInt(1, studentID);
            ResultSet amountResult = checkAmountOfStudentRentalsStmt.executeQuery();

            if (amountResult.next()){
                return !amountResult.getBoolean("has_booked_multiple_instruments");
            }
            else{
                throw new RentalDBException(failureMsg);
            }
        }
        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        return null;
    }

    /**
     * Checks if a rental is terminated.
     * 
     * @param instrumentID The ID of the instrument.
     * @param studentID The ID of the student.
     * @return A boolean about whether or not it's terminated.
     * @throws RentalDBException If it fails at checking whether the rental is terminated.
     */
    public Boolean isTerminated(Integer instrumentID, Integer studentID) throws RentalDBException{
        String failureMsg = "Could find information about if the rental is terminated.";
        try{
            checkTerminationStmt.setInt(1, instrumentID);
            checkTerminationStmt.setInt(2, studentID);
            ResultSet checkResult = checkTerminationStmt.executeQuery();
            if (checkResult.next()) {
                return checkResult.getBoolean("is_terminated");
            }
            else{
                throw new RentalDBException(failureMsg);
            }
        }
        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        return null;
    }

    /**
     * Terminates a rental.
     * 
     * @param instrumentID The ID of the instrument.
     * @param studentID The ID of the student.
     * @return Information of the termination.
     * @throws RentalDBException If it fails at terminating the rental.
     */
    public Rental terminateRental(Integer instrumentID, Integer studentID) throws RentalDBException {
        String failureMsg = "Could not terminate rental.";
        try{
            terminateRentalStmt.setInt(1, instrumentID);
            terminateRentalStmt.setInt(2, studentID);
            int updatedRows = terminateRentalStmt.executeUpdate();

            if (updatedRows != 1) {
                throw new RentalDBException(failureMsg);
            }

            long currentTimeMillis = System.currentTimeMillis();
            long roundedTimeMillis = currentTimeMillis - (currentTimeMillis % 1000);
            Timestamp currentTimestamp = new Timestamp(roundedTimeMillis);

            return new Rental(instrumentID, studentID, currentTimestamp);
        }
        catch(SQLException sqle){
            handleException(failureMsg, sqle);
        }
        return null;
    }   
   
    private void connectToBankDB() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/IV1351 2.0",
                "postgres", "root");
        // connection =
        // DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb",
        // "mysql", "mysql");
        connection.setAutoCommit(false);
    }

    private void prepareStatements() throws SQLException {
                
        findAllRentableInstrumentsStmt = connection.prepareStatement(
            "SELECT i.* FROM " + INSTRUMENTS_TABLE_NAME + " i " +
            "LEFT JOIN " + RENTAL_TABLE_NAME + " r ON i." + INSTRUMENT_ID_COLUMN_NAME + " = r." + RENTAL_INSTRUMENT_ID_COLUMN_NAME + " " +
            "WHERE (r." + RENTAL_END_DATE_COLUMN_NAME + " IS NULL OR r." + RENTAL_END_DATE_COLUMN_NAME + " <= CURRENT_DATE) " +
            "AND i." + INSTRUMENT_TYPE_COLUMN_NAME + " = ? " +
            "AND NOT EXISTS (" +
                "SELECT 1 FROM " + RENTAL_TABLE_NAME +
                " WHERE " + RENTAL_INSTRUMENT_ID_COLUMN_NAME + " = i." + INSTRUMENT_ID_COLUMN_NAME +
                " AND " + RENTAL_TERMINATED_RENTAL_COLUMN_NAME + " IS NULL AND " +
                RENTAL_END_DATE_COLUMN_NAME + " > CURRENT_DATE" +
            ")"
        );

        checkAmountOfStudentRentalsStmt = connection.prepareStatement("SELECT CASE WHEN COUNT(DISTINCT " + RENTAL_TABLE_NAME + 
        ") >= 2 THEN TRUE ELSE FALSE END AS has_booked_multiple_instruments FROM " + RENTAL_TABLE_NAME + 
        " WHERE " + RENTAL_STUDENT_ID_COLUMN_NAME + " = ? AND " + RENTAL_TERMINATED_RENTAL_COLUMN_NAME + 
        " IS NULL AND " + RENTAL_END_DATE_COLUMN_NAME + " > CURRENT_TIMESTAMP;");

        checkInstrumentAvailabilityStmt = connection.prepareStatement("SELECT NOT EXISTS (SELECT 1 FROM " + RENTAL_TABLE_NAME + 
        " WHERE " + RENTAL_INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + RENTAL_TERMINATED_RENTAL_COLUMN_NAME + 
        " IS NULL AND " + RENTAL_END_DATE_COLUMN_NAME + " > CURRENT_TIMESTAMP) AS is_instrument_available;");

        addRentalStmt = connection.prepareStatement("INSERT INTO " + RENTAL_TABLE_NAME + " (" +
        RENTAL_INSTRUMENT_ID_COLUMN_NAME + ", " + RENTAL_STUDENT_ID_COLUMN_NAME + ", " + RENTAL_START_DATE_COLUMN_NAME + ", " + RENTAL_END_DATE_COLUMN_NAME +
        ")VALUES (?, ?, ?, ?)");

        checkTerminationStmt = connection.prepareStatement("SELECT EXISTS (SELECT 1 FROM " + RENTAL_TABLE_NAME +
        " WHERE " + RENTAL_INSTRUMENT_ID_COLUMN_NAME + " = ? AND " +
        RENTAL_STUDENT_ID_COLUMN_NAME + " = ? AND " +
        RENTAL_TERMINATED_RENTAL_COLUMN_NAME + " IS NOT NULL) AS is_terminated;");


        terminateRentalStmt = connection.prepareStatement("UPDATE " + RENTAL_TABLE_NAME + 
        " SET " + RENTAL_TERMINATED_RENTAL_COLUMN_NAME + " = CURRENT_TIMESTAMP " + 
        " WHERE " + RENTAL_INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + 
        RENTAL_STUDENT_ID_COLUMN_NAME + " = ? AND " + 
        RENTAL_TERMINATED_RENTAL_COLUMN_NAME + " IS NULL");
    }

    private void handleException(String failureMsg, Exception cause) throws RentalDBException {
        String completeFailureMsg = failureMsg;
        try {
            connection.rollback();
        } catch (SQLException rollbackExc) {
            completeFailureMsg = completeFailureMsg +
                    ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }

        if (cause != null) {
            throw new RentalDBException(failureMsg, cause);
        } else {
            throw new RentalDBException(failureMsg);
        }
    }

    
}
