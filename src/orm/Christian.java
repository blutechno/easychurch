package orm;
// Generated Jun 18, 2012 3:59:06 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Christian generated by hbm2java
 */
public class Christian  implements java.io.Serializable {


     private Integer christianId;
     private Parish parish;
     private String firstName;
     private String lastName;
     private String middleName;
     private String fullName;
     private Date dateOfBirth;
     private char gender;
     private String fatherName;
     private String motherName;
     private char maritalStatus;
     private String educationLevel;
     private String profession;
     private Date createdOn;
     private char isActive;
     private String christianPin;
     private Date baptismDate;
     private Character baptisedHere;
     private String spouseFirstName;
     private String spouseLastName;
     private Set eventInviteds = new HashSet(0);
     private Set christianSacrements = new HashSet(0);
     private Set christianContacts = new HashSet(0);
     private Set prayerRequests = new HashSet(0);
     private Set loans = new HashSet(0);
     private Set christianZones = new HashSet(0);

    public Christian() {
    }

	
    public Christian(Parish parish, String firstName, String lastName, String fullName, Date dateOfBirth, char gender, String fatherName, String motherName, char maritalStatus, String educationLevel, String profession, Date createdOn, char isActive, String christianPin) {
        this.parish = parish;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.maritalStatus = maritalStatus;
        this.educationLevel = educationLevel;
        this.profession = profession;
        this.createdOn = createdOn;
        this.isActive = isActive;
        this.christianPin = christianPin;
    }
    public Christian(Parish parish, String firstName, String lastName, String middleName, String fullName, Date dateOfBirth, char gender, String fatherName, String motherName, char maritalStatus, String educationLevel, String profession, Date createdOn, char isActive, String christianPin, Date baptismDate, Character baptisedHere, String spouseFirstName, String spouseLastName, Set eventInviteds, Set christianSacrements, Set christianContacts, Set prayerRequests, Set loans, Set christianZones) {
       this.parish = parish;
       this.firstName = firstName;
       this.lastName = lastName;
       this.middleName = middleName;
       this.fullName = fullName;
       this.dateOfBirth = dateOfBirth;
       this.gender = gender;
       this.fatherName = fatherName;
       this.motherName = motherName;
       this.maritalStatus = maritalStatus;
       this.educationLevel = educationLevel;
       this.profession = profession;
       this.createdOn = createdOn;
       this.isActive = isActive;
       this.christianPin = christianPin;
       this.baptismDate = baptismDate;
       this.baptisedHere = baptisedHere;
       this.spouseFirstName = spouseFirstName;
       this.spouseLastName = spouseLastName;
       this.eventInviteds = eventInviteds;
       this.christianSacrements = christianSacrements;
       this.christianContacts = christianContacts;
       this.prayerRequests = prayerRequests;
       this.loans = loans;
       this.christianZones = christianZones;
    }
   
    public Integer getChristianId() {
        return this.christianId;
    }
    
    public void setChristianId(Integer christianId) {
        this.christianId = christianId;
    }
    public Parish getParish() {
        return this.parish;
    }
    
    public void setParish(Parish parish) {
        this.parish = parish;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMiddleName() {
        return this.middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getFullName() {
        return this.fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public char getGender() {
        return this.gender;
    }
    
    public void setGender(char gender) {
        this.gender = gender;
    }
    public String getFatherName() {
        return this.fatherName;
    }
    
    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
    public String getMotherName() {
        return this.motherName;
    }
    
    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }
    public char getMaritalStatus() {
        return this.maritalStatus;
    }
    
    public void setMaritalStatus(char maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public String getEducationLevel() {
        return this.educationLevel;
    }
    
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
    public String getProfession() {
        return this.profession;
    }
    
    public void setProfession(String profession) {
        this.profession = profession;
    }
    public Date getCreatedOn() {
        return this.createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    public char getIsActive() {
        return this.isActive;
    }
    
    public void setIsActive(char isActive) {
        this.isActive = isActive;
    }
    public String getChristianPin() {
        return this.christianPin;
    }
    
    public void setChristianPin(String christianPin) {
        this.christianPin = christianPin;
    }
    public Date getBaptismDate() {
        return this.baptismDate;
    }
    
    public void setBaptismDate(Date baptismDate) {
        this.baptismDate = baptismDate;
    }
    public Character getBaptisedHere() {
        return this.baptisedHere;
    }
    
    public void setBaptisedHere(Character baptisedHere) {
        this.baptisedHere = baptisedHere;
    }
    public String getSpouseFirstName() {
        return this.spouseFirstName;
    }
    
    public void setSpouseFirstName(String spouseFirstName) {
        this.spouseFirstName = spouseFirstName;
    }
    public String getSpouseLastName() {
        return this.spouseLastName;
    }
    
    public void setSpouseLastName(String spouseLastName) {
        this.spouseLastName = spouseLastName;
    }
    public Set getEventInviteds() {
        return this.eventInviteds;
    }
    
    public void setEventInviteds(Set eventInviteds) {
        this.eventInviteds = eventInviteds;
    }
    public Set getChristianSacrements() {
        return this.christianSacrements;
    }
    
    public void setChristianSacrements(Set christianSacrements) {
        this.christianSacrements = christianSacrements;
    }
    public Set getChristianContacts() {
        return this.christianContacts;
    }
    
    public void setChristianContacts(Set christianContacts) {
        this.christianContacts = christianContacts;
    }
    public Set getPrayerRequests() {
        return this.prayerRequests;
    }
    
    public void setPrayerRequests(Set prayerRequests) {
        this.prayerRequests = prayerRequests;
    }
    public Set getLoans() {
        return this.loans;
    }
    
    public void setLoans(Set loans) {
        this.loans = loans;
    }
    public Set getChristianZones() {
        return this.christianZones;
    }
    
    public void setChristianZones(Set christianZones) {
        this.christianZones = christianZones;
    }




}


