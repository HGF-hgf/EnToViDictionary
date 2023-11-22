package com.example.dictionaryy;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AddWordService extends Service<Void> {
    public String file;
    public AddWordService(String file){
        this.file = file;
    }

    @Override
    protected Task<Void> createTask(){
        return  new AddWordTask(file);
    }

}
