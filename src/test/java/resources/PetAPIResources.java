package resources;
//enum is special class in java which has collection of constants or  methods
public enum PetAPIResources {

	AddPetAPI("/pet"),
	getPetAPI("/pet/{petId}"),
	UpdateAPI("/pet/{petId}"),
	deletePetAPI("/pet/{petId}"),
	findByStatusAPI("/pet/findByStatus"),
	findByTagsAPI("pet/findByTags");

	private String resource;

	PetAPIResources(String resource)
	{
		this.resource=resource;
	}
	
	public String getResource()
	{
		return resource;
	}
}
