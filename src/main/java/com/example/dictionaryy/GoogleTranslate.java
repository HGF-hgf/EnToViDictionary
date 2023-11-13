/*
 * Copyright 2016 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*


*/
/*
 * EDITING INSTRUCTIONS
 * This file is referenced in READMEs and javadoc. Any change to this file should be reflected in
 * the project's READMEs and package-info.java.
 *//*


package com.example.dictionaryy;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

*/
/**
 * A snippet for Google Translation showing how to detect the language of some text and translate
 * some other text.
 *//*



public class GoogleTranslate {

    public static void main(String... args) {
        try {
            Translate translate = TranslateOptions.getDefaultInstance().getService();

            final String mysteriousText = "Hola Mundo";

            Detection detection = translate.detect(mysteriousText);
            String detectedLanguage = detection.getLanguage();

            Translation translation =
                    translate.translate(
                            mysteriousText,
                            TranslateOption.sourceLanguage(detectedLanguage),
                            TranslateOption.targetLanguage("en"));

            System.out.println(translation.getTranslatedText());
        } catch (Exception e) {
            System.out.println("Exception occurred");
        }
    }
}
*/
