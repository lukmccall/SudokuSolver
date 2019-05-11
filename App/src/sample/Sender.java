package App.src.sample;

import java.awt.image.BufferedImage;

public interface Sender {
     void send(BufferedImage image, Parameters parameters) throws Exception;
     void solve() throws Exception;
}
