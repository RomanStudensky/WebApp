package ru.webApp.models;

import lombok.Data;

@Data
public class ChangePostContract extends Contract{
    int id;
    String newPost;

    public ChangePostContract() {
        newPost = new String();
    }

    public ChangePostContract(int id, int idChangePost, String dateBegin, String dateEnd,
                              boolean resolution, String newPost) {
        super(id, dateBegin, dateEnd, resolution);
        this.id = idChangePost;
        this.newPost = newPost;
    }
}
