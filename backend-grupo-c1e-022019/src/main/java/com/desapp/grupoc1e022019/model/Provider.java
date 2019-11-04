package com.desapp.grupoc1e022019.model;

import com.desapp.grupoc1e022019.exception.InsufficientCreditException;
import com.desapp.grupoc1e022019.exception.MaximumMenusSizeException;
import com.desapp.grupoc1e022019.model.providerComponents.providerState.ProviderState;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.BussinessTime;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.Schedule;
import com.desapp.grupoc1e022019.model.providerComponents.location.Address;
import com.desapp.grupoc1e022019.model.providerComponents.schedule.SetOfBussinessTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
public class Provider extends EntityId{

    private String name;
    private String logo;
    private String city;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_ADDRESS")
    private Address address;
    private String description;
    private String webURL;
    @Column(unique = true)
    private String email;
    private String telNumber;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_SCHEDULE")
    private Schedule schedule;
    private Double deliveryMaxDistanceInKM;
    @JsonManagedReference
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "provider"
    )
    private List<Menu> menus;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_CREDIT")
    private Credit credit;
    private Integer strikesMenu;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ID_PROVIDER_STATE")
    private ProviderState providerState;

    public Provider(String name, String logo, String city, Address address, String description, String webURL,
                    String email, String telNumber, Schedule schedule, Credit credit, Double deliveryMaxDistanceInKM,
                    List<Menu> menus,ProviderState providerState,Integer strikesMenu) {

        setName(name);
        setLogo(logo);
        setCity(city);
        setAddress(address);
        setDescription(description);
        setWebURL(webURL);
        setEmail(email);
        setTelNumber(telNumber);
        setSchedule(schedule);
        setDeliveryMaxDistanceInKM(deliveryMaxDistanceInKM);
        this.menus = menus;
        this.credit = credit;
        setStrikesMenu(strikesMenu);
        this.providerState = providerState;
    }

    public Provider(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Double getDeliveryMaxDistanceInKM() {
        return deliveryMaxDistanceInKM;
    }

    public void setDeliveryMaxDistanceInKM(Double deliveryMaxDistanceInKM) {
        this.deliveryMaxDistanceInKM = deliveryMaxDistanceInKM;
    }

    public Integer getStrikesMenu() {
        return strikesMenu;
    }

    public void setStrikesMenu(Integer strikesMenu) {
        this.strikesMenu = strikesMenu;
    }

    public void setProviderState(ProviderState providerState){
        this.providerState = providerState;
    }

    public void recievesCredit(Credit receiveCredit){
        this.credit = this.credit.sum(receiveCredit);
    }

    public Credit getCredit(){
        return this.credit;
    }

    public Credit withdrawCredit(Credit amountToWithdraw){
        if(! this.credit.isGreaterOrEqual(amountToWithdraw)){
            throw new InsufficientCreditException("Insufficient credits");
        }

        this.credit = this.credit.minus(amountToWithdraw);
        return amountToWithdraw;
    }

    public void addAStrike(){
        providerState.addStrike(this);
    }

    public boolean isPenalized(){
        return providerState.isPenalized();
    }

    public boolean isNormalProvider(){
        return providerState.isNormalProvider();
    }

    public String getProviderStateName(){
        return providerState.toString();
    }

    public Map<DayOfWeek, SetOfBussinessTime> getMapSchedule(){
        return schedule.getDaysAndBussinessTime();
    }

    public Schedule getSchedule(){return this.schedule;}

    public void deleteBussinessTime(DayOfWeek day){
        this.schedule.deleteBussinessTime(day);
    }

    public void addBussinessTime(DayOfWeek day,BussinessTime newBussinessHour){
        this.schedule.addBussinessTime(day,newBussinessHour);
    }

    public void setBussinessTime(DayOfWeek day,SetOfBussinessTime newBussinessHours){
        this.schedule.setBussinessTime(day,newBussinessHours);
    }

    public Set<BussinessTime> getBussinessHoursOf(DayOfWeek day){
        return schedule.getBussinessHoursOf(day);
    }

    public Set<DayOfWeek> getScheduleDays(){
        return schedule.getDays();
    }

    public Collection<SetOfBussinessTime> getScheduleBussinessTimes(){
        return schedule.getBussinessTimes();
    }

    private void setSchedule(Schedule schedule){
        this.schedule = schedule;
    }

    private void checkMaxMenus(){
        if(this.menus.size() >= 20){
            throw new MaximumMenusSizeException("Can't add more than twenty menus");
        }
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void addMenu(Menu menu) {
        checkMaxMenus();
        menus.add(menu);
    }

    public void deleteMenu(Menu menu) {
        menus.remove(menu);
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public ProviderState getProviderState() {
        return providerState;
    }
}
