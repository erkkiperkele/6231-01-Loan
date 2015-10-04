package Transport;

import java.io.*;
import java.net.*;

public class UDPClient implements Closeable {

    private DatagramSocket _socket;
    private InetAddress _host;
    private static final int TIME_OUT = 3000;


    public UDPClient() {

        try {
            _socket = new DatagramSocket();
            _host = InetAddress.getByName("localhost");
            _socket.setSoTimeout(TIME_OUT);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String sendMessage(String message, int serverPort) throws IOException {

        byte[] m = message.getBytes();

        DatagramPacket request = new DatagramPacket(m, message.length(), _host, serverPort);
        _socket.send(request);

        byte[] buffer = new byte[1000];
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
        _socket.receive(reply);

        return new String(reply.getData());
    }

    @Override
    public void close() throws IOException {
            if (_socket != null) {
                _socket.close();
            }
    }
}
