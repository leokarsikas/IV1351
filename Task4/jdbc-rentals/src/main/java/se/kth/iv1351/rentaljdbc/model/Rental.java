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

package se.kth.iv1351.rentaljdbc.model;

import java.sql.Timestamp;

/**
 * An instrument in the system.
 */
public class Rental implements RentalDTO {
    private int instrumentID;
    private int studentID;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp terminatedRental;

    /**
     * Creates an instrument with the specified id, type, brand and renting fee.
     *
     * @param id            The account number.
     * @param type          The account holder's holderName.
     * @param brand         The initial balance.
     * @param rentingFee    The initial balance.
     */
    public Rental(int instrumentID, int studentID, Timestamp startDate, Timestamp endDate) {
        this.instrumentID = instrumentID;
        this.studentID = studentID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Rental(int instrumentID, int studentID, Timestamp terminatedRental) {
        this.instrumentID = instrumentID;
        this.studentID = studentID;
        this.terminatedRental = terminatedRental;
    }

    /**
     * @return The instrument id.
     */
    public int getInstrumentID() {
        return instrumentID;
    }

    /**
     * @return The instrument type.
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     * @return The instrument brand.
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @return The instruments renting fee.
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @return The instruments renting fee.
     */
    public Timestamp getTerminatedRental() {
        return terminatedRental;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Rental: [");
        stringRepresentation.append("instrument id: ");
        stringRepresentation.append(instrumentID);
        stringRepresentation.append(", student id: ");
        stringRepresentation.append(studentID);
        if(startDate != null){
            stringRepresentation.append(", start date: ");
            stringRepresentation.append(startDate);
        }
        if(endDate != null){
            stringRepresentation.append(", end date: ");
            stringRepresentation.append(endDate);
        }
        if(terminatedRental != null){
            stringRepresentation.append(", terminated rental: ");
            stringRepresentation.append(terminatedRental);
        }
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
