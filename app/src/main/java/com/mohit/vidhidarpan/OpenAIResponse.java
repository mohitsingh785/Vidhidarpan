package com.mohit.vidhidarpan;

import java.util.List;

public class OpenAIResponse {
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        private String text;

        public String getText() {
            return text;
        }
    }

    @Override
    public String toString() {
        if (choices != null && !choices.isEmpty()) {
            return choices.get(0).getText();
        }
        return "No response";
    }
}
