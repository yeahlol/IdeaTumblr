package net.yeahlol.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import net.yeahlol.lucene.PartOfSpeechAttribute.PartOfSpeech;

public class PartOfSpeechTaggingFilter extends TokenFilter {
    PartOfSpeechAttribute posAtt = addAttribute(PartOfSpeechAttribute.class);
    CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    public PartOfSpeechTaggingFilter(TokenStream input) {
        super(input);
    }

    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }
        posAtt.setPartOfSpeech(daterminePos(termAtt.buffer(), 0, termAtt.length()));
        return true;
    }

    protected PartOfSpeech daterminePos(char[] term, int offset, int length) {
        if (length > 0 && Character.isUpperCase(term[0])) {
            return PartOfSpeech.Noun;
        }
        return PartOfSpeech.Unknown;
    }
}