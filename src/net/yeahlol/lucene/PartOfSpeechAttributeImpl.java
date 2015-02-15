package net.yeahlol.lucene;

import org.apache.lucene.util.AttributeImpl;

public class PartOfSpeechAttributeImpl extends AttributeImpl implements PartOfSpeechAttribute {
    private PartOfSpeech pos = PartOfSpeech.Unknown;

    public void setPartOfSpeech(PartOfSpeech pos) {
        this.pos = pos;
    }

    public PartOfSpeech getPartOfSpeech() {
        return pos;
    }

    @Override
    public void clear() {
        pos = PartOfSpeech.Unknown;
    }

    @Override
    public void copyTo(AttributeImpl target) {
        ((PartOfSpeechAttribute) target).setPartOfSpeech(pos);
    }
}
