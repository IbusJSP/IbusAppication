package com.ibus.dto;
	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.RequiredArgsConstructor;
	import lombok.Setter;

	import javax.validation.constraints.NotNull;
	import javax.validation.constraints.Size;

	@Getter
	@Setter
	@AllArgsConstructor
	@RequiredArgsConstructor
	public class AddressDto {

	    @NotNull
	    @Size(max = 10)
	    private String houseNumber;

	    @NotNull
	    @Size(max = 50)
	    private String street;

	    @NotNull
	    @Size(max = 30)
	    private String city;

	    @NotNull
	    private int pinCode;

	    @NotNull
	    @Size(max = 30)
	    private String state;

	    @NotNull
	    @Size(max = 30)
	    private String country;
	


}
