package resources;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonObject;
import pojo.AddPet;
import pojo.Category;
import pojo.Tags;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;

public class PetDataBuilder {


    public AddPet addPetLoad(String id, String petName, String category, String photoURL, String tagName, String status)
    {
        AddPet petPayload = new AddPet();
        petPayload.setId(Integer.parseInt(id));

        Category categoryDetails = new Category();
        categoryDetails.setId(Integer.parseInt(id));
        categoryDetails.setName(category);
        petPayload.setCategory(categoryDetails);

        petPayload.setName(petName);

        List<String> myList =new ArrayList<String>();
        myList.add(photoURL);
        petPayload.setPhotoUrls(myList);


        Tags tag1 = new Tags();
        tag1.setId(1);
        tag1.setName(tagName);
        List<Tags> myTags =new ArrayList<Tags>();
        myTags.add(tag1);

        petPayload.setTags(myTags);

        petPayload.setStatus(status);

        return petPayload;
    }
}
