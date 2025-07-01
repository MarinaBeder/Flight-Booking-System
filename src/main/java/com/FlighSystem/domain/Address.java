package  com.FlighSystem.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	  private String streetNo;
	    private String neighbourhood;
	    private String city;
	    private String country;
}
