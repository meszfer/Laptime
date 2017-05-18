package app.andrey_voroshkov.chorus_laptimer;

/**
 * Created by meszfer on 2017.05.17..
 */

public class ChannelBandFreq {

    private String channel;
    private int band;
    private int freq;

    public ChannelBandFreq(String channel, int band, int freq) {
        this.channel = channel;
        this.band = band;
        this.freq = freq;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }
}
