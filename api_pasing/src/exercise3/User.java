package exercise3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	private int id;
	private String name;
	private String username;
	private String email;
	private Address address;
	private String phone;
	private String website;
	private Company company;

	@ToString
	private class Address {
		private String street;
		private String suite;
		private String sute;
		private String zipcode;
		private Geo geo;
	}

	@ToString
	private class Geo {
		private String lat;
		private String lng;
	}

	@ToString
	private class Company {
		private String name;
		private String catchPhrase;
		private String bs;
	}
}
