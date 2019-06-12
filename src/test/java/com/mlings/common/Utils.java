/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mlings.common;

import io.restassured.RestAssured;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Random;

/**
 *
 * @author Mahesh Lingsugur
 */
public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());
    private final String RANDOM_QUOTE_REST_API = "https://thesimpsonsquoteapi.glitch.me/quotes"; 
    private final String GoogleTranslateApi = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=auto&tl=%s&dt=t&q=[%s]";
    private final String[] googleTranlateLocales = {"af", "sq", "ar", "az", "eu", "bn", "be", "bg", "ca", "zh-CN", "zh-TW", "hr", "cs", "da", "nl", "en", 
        "eo", "et", "tl", "fi", "fr", "gl", "ka", "de", "el", "gu", "ht", "iw", "hi", "hu", "is", "id", "ga", "it", "ja", "kn", "ko", "la", "lv", "lt", 
        "mk", "ms", "mt", "no", "fa", "pl", "pt", "ro", "ru", "sr", "sk", "sl", "es", "sw", "sv", "ta", "te", "th", "tr", "uk", "ur", "vi", "cy", "yi"};
    
    ConfigReader conf = ConfigReader.getInstance();

    public String testStartTimeStamp(int length) {
        return testStartTimeStamp(length, false);
    }

    public String testStartTimeStamp(int length, boolean onlyDigits) {
        String timeStamp = conf.getValue("TestStartTimeStamp");
        if (onlyDigits) {
            timeStamp = timeStamp.replaceAll("\\D+", "");
        }
        return timeStamp.substring(0, Math.min(length, timeStamp.length()));
    }

    public String getRandomString(int length, boolean letters, boolean numbers){
        return RandomStringUtils.random(length, letters, numbers);
    }
    
    public int getRandomInt(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
    
    public String getRandomQuoteEn(){
        return strSimplify(restGet(RANDOM_QUOTE_REST_API, "[0].quote"));
    }
    
    public String getRandomQuoteRandomLang(){
        String randomLang = googleTranlateLocales[getRandomInt(0, googleTranlateLocales.length)];
        return googleTranslate(randomLang, getRandomQuoteEn());
    }
    
    public String getRandomQuoteInLang(String langCode){
        return googleTranslate(langCode, getRandomQuoteEn());
    }

    public String googleTranslate(String langCode, String text) {
        text = strSimplify(restGet(String.format(GoogleTranslateApi, langCode, text), "[0][0][0]"));
        return text;
    }
    
    public String restGet(String apiToGet, String jsonPath) {
        String restResponse = RestAssured.given().get(apiToGet).path(jsonPath).toString();
        LOGGER.log(Level.INFO, "Response: " + restResponse);
        return restResponse;
    }
    
    public String strSimplify(String str) {
        str = str.replace("\n", "")
                .replace("\r", "")
                .replace("[", "")
                .replace("  ", " ")
                .replace("]", "");
        return str;
    }
            
}
