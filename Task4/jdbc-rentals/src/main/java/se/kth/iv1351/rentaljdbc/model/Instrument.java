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

/**
 * An instrument in the system.
 */
public class Instrument implements InstrumentDTO {
    private int id;
    private String type;
    private String brand;
    private int rentingFee;

    /**
     * Creates an instrument with the specified id, type, brand and renting fee.
     *
     * @param id            The account number.
     * @param type          The account holder's holderName.
     * @param brand         The initial balance.
     * @param rentingFee    The initial balance.
     */
    public Instrument(int id, String type, String brand, int rentingFee) {
        this.id = id;
        this.type = type;
        this.brand = brand;
        this.rentingFee = rentingFee;
    }

    /**
     * @return The instrument id.
     */
    public int getID() {
        return id;
    }

    /**
     * @return The instrument type.
     */
    public String getType() {
        return type;
    }

    /**
     * @return The instrument brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @return The instruments renting fee.
     */
    public int getRentingFee() {
        return rentingFee;
    }

    /**
     * @return A string representation of all fields in this object.
     */
    @Override
    public String toString() {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.append("Instrument: [");
        stringRepresentation.append("id: ");
        stringRepresentation.append(id);
        stringRepresentation.append(", type: ");
        stringRepresentation.append(type);
        stringRepresentation.append(", brand: ");
        stringRepresentation.append(brand);
        stringRepresentation.append(", renting fee: ");
        stringRepresentation.append(rentingFee);
        stringRepresentation.append("]");
        return stringRepresentation.toString();
    }
}
