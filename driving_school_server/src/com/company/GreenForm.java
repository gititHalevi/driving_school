package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GreenForm implements Writable{
    public static final int VALIDITY_YEARS = 3;
    private Date eyeDate;
    private Date medicalDate;
    private Date theoryDate;

    public GreenForm(Date eyeTestDate, Date medicalCertificateDate, Date theoryDate) {
        this.eyeDate = new Date(eyeTestDate);
        this.medicalDate = new Date(medicalCertificateDate);
        this.theoryDate = new Date(theoryDate);

    }
    public GreenForm(GreenForm greenForm){
        this(greenForm.eyeDate,greenForm.medicalDate,greenForm.theoryDate);
    }
    public GreenForm(InputStream inputStream) throws IOException{
        read(inputStream);
    }
    public Date getEyeDate() {
        return new Date(eyeDate);
    }
    public void setEyeDate(Date eyeDate) {
        this.eyeDate = new Date(eyeDate);
    }
    public Date getMedicalDate() {
        return new Date(medicalDate);

    }
    public void setMedicalDate(Date medicalDate) {
        this.medicalDate = new Date(medicalDate);
    }
    public Date getTheoryDate() {
        return new Date(theoryDate);
    }
    public void setTheoryDate(Date theoryDate) {
        this.theoryDate = new Date(theoryDate);
    }
    public static int getValidityYears() {
        return VALIDITY_YEARS;
    }
    public Date getEyeValidity() {
        return eyeDate.enlargeByYears(VALIDITY_YEARS);
    }
    public Date getMedicalValidity() {
        return medicalDate.enlargeByYears(VALIDITY_YEARS);
    }
    public Date getTheoryValidity() {
        return theoryDate.enlargeByYears(VALIDITY_YEARS);
    }
    public Date getGreenFormValidity() {
        return Date.smallOut(new Date[]{getEyeValidity(),getMedicalValidity(),getTheoryValidity()});
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(object instanceof GreenForm))
            return false;
        GreenForm other = (GreenForm) object;
        return eyeDate.equals(other.eyeDate) && medicalDate.equals(other.medicalDate)
                && theoryDate.equals(other.theoryDate);
    }
    @Override
    public int hashCode() {
        return eyeDate.hashCode()*57 ^ medicalDate.hashCode()*27 ^ theoryDate.hashCode()*13 ;
    }
    @Override
    public String toString() {
        return "GREEN FORM: (validity - " + getGreenFormValidity()
                + ")\n    Eye test date: " + eyeDate + " (validity: " + getEyeValidity() +
                ")\n    Medical certificate date: " + medicalDate + " (validity: " + getMedicalValidity() +
                ")\n    Theory date: " + theoryDate + " (validity: " + getTheoryValidity() + ")";
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        eyeDate.write(outputStream);
        medicalDate.write(outputStream);
        theoryDate.write(outputStream);
    }

    @Override
    public void read(InputStream inputStream) throws IOException {
        eyeDate = new Date(inputStream);
        medicalDate = new Date(inputStream);
        theoryDate = new Date(inputStream);
    }
}
