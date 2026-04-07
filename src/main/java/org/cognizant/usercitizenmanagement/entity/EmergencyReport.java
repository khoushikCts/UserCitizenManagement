
package org.cognizant.usercitizenmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.cognizant.usercitizenmanagement.Enum.EmergencyType;
import org.cognizant.usercitizenmanagement.Enum.ReportStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "EmergencyReport")
public class EmergencyReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmergencyType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    @CreationTimestamp
    private LocalDateTime date;

    // MANY reports → ONE citizen
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CitizenID")
    @JsonBackReference
    private Citizen citizen;

    // ONE report → MANY incidents
//    @OneToMany(mappedBy = "emergencyReport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JsonManagedReference
//    private List<Incident> incidents = new ArrayList<>();

    public EmergencyReport() {}

    // GETTERS AND SETTERS
//
//    public int getReportId() { return reportId; }
//    public void setReportId(int reportId) { this.reportId = reportId; }
//
//    public String getLocation() { return location; }
//    public void setLocation(String location) { this.location = location; }
//
//    public EmergencyType getType() { return type; }
//    public void setType(EmergencyType type) { this.type = type; }
//
//    public ReportStatus getStatus() { return status; }
//    public void setStatus(ReportStatus status) { this.status = status; }
//
//    public LocalDateTime getDate() { return date; }
//
//    public Citizen getCitizen() { return citizen; }
//    public void setCitizen(Citizen citizen) { this.citizen = citizen; }
//
//    public List<Incident> getIncidents() { return incidents; }
//    public void setIncidents(List<Incident> incidents) { this.incidents = incidents; }
}