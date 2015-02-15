package net.yeahlol.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.yeahlol.lucene.PartOfSpeechAttribute;
import net.yeahlol.lucene.PartOfSpeechTaggingFilter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TokenizeUtils {
    private Analyzer analyzer;

    public TokenizeUtils() {
        analyzer = getAnalyzer();
    }

    private Analyzer getAnalyzer() {
        Analyzer analyzer = new WhitespaceAnalyzer();
        return analyzer;
    }

    public List<String> getTokens(String text) {
        List<String> tokenList = new ArrayList<String>();
        try {
            TokenStream tokenStream = analyzer.tokenStream("content", text);
            tokenStream = new PartOfSpeechTaggingFilter(tokenStream);
            CharTermAttribute charAtt = tokenStream.addAttribute(CharTermAttribute.class);
            PartOfSpeechAttribute posAtt = tokenStream.addAttribute(PartOfSpeechAttribute.class);

            tokenStream.reset();
            while(tokenStream.incrementToken()) {
                if (posAtt.getPartOfSpeech().toString().equals("Noun")) {
                    tokenList.add(charAtt.toString());
                }
            }
            tokenStream.end();
            tokenStream.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return tokenList;
    }

}
