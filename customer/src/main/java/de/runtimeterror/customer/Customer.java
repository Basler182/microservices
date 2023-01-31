package de.runtimeterror.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.runtimeterror.customer.rights.CustomerRights;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    @JsonIgnore
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @ElementCollection
    @CollectionTable(
            name = "customer_rights",
            joinColumns = @JoinColumn(name = "customer_id")
    )
    private List<CustomerRights> rights;
}
