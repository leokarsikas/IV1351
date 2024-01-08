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

package se.kth.iv1351.rentaljdbc.controller;

import se.kth.iv1351.rentaljdbc.integration.RentalDAO;
import se.kth.iv1351.rentaljdbc.integration.RentalDBException;
import se.kth.iv1351.rentaljdbc.model.InstrumentException;
import se.kth.iv1351.rentaljdbc.model.RentalException;
import se.kth.iv1351.rentaljdbc.model.InstrumentDTO;
import se.kth.iv1351.rentaljdbc.model.RentalDTO;

import java.util.List;
import java.sql.Timestamp;

/**
 * This is the application's only controller, all calls to the model pass here.
 * The controller is also responsible for calling the DAO. Typically, the
 * controller first calls the DAO to retrieve data (if needed), then operates on
 * the data, and finally tells the DAO to store the updated data (if any).
 */
public class Controller {
    private final RentalDAO rentalDb;

    /**
     * Creates a new instance, and retrieves a connection to the database.
     * 
     * @throws RentalDBException If unable to connect to the database.
     */
    public Controller() throws RentalDBException {
        rentalDb = new RentalDAO();
    }

    /**
     * Gets and returns the list of instruments.
     * 
     * @return The list of instruments.
     * @throws InstrumentException If it failed at getting the instruments.
     */
    public List<? extends InstrumentDTO> listInstruments() throws InstrumentException{
        try {
            return rentalDb.findInstruments();
        }
        catch (Exception e) {
            throw new InstrumentException("Unable to list instruments.", e);
        }
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
    public RentalDTO rentInstrument(Integer instrumentID, Integer studentID, String startDate, String endDate) throws RentalException{
        String failureMsg = "Could not create rental";
        String instrumentNotAvailableMsg = "This instrument is not available";
        String tooManyInstrumentsMsg = "This student already has 2 instruments rented.";
        RentalDTO rental = null;

        if(instrumentID == null || studentID == null || startDate == null || endDate == null){
            throw new RentalException(failureMsg);
        }

        try {
            if(!rentalDb.isInstrumentRentable(instrumentID)){
                throw new RentalException(instrumentNotAvailableMsg);
            }
            else if(!rentalDb.isStudentAllowedToRent(studentID)){
                throw new RentalException(tooManyInstrumentsMsg);
            }
            else{
                rental = rentalDb.rentInstrument(instrumentID, studentID, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
            }
        }
        catch (Exception e) {
            throw new RentalException(failureMsg, e);
        }

        return rental;
    }
    /**
     * Terminates a rental.
     * 
     * @param instrumentID The ID of the instrument.
     * @param studentID The ID of the student.
     * @return Information of the termination.
     * @throws RentalDBException If it fails at terminating the rental.
     */
    public RentalDTO terminateRental(Integer instrumentID, Integer studentID) throws RentalException{
        String failureMsg = "Could not create rental.";
        String isAlreadyTerminatedMsg = "The rental is already terminated.";
        if(instrumentID == null || studentID == null){
            throw new RentalException(failureMsg);
        }

        try{
            if(rentalDb.isTerminated(instrumentID, studentID)){
                throw new RentalException(isAlreadyTerminatedMsg);
            }
            else{
                return rentalDb.terminateRental(instrumentID, studentID);
            }
        }
        catch (Exception e) {
            throw new RentalException("Unable to terminate rental.", e);
        }
    }
}
