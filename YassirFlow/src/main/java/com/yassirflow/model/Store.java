package com.yassirflow.model;

import com.yassirflow.model.enums.StoreStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String brand;

    private String description;

    private String storeType;

    private StoreStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private StoreContact contact= new StoreContact();

    @OneToOne
    private User storeAdmin;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        status=StoreStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
