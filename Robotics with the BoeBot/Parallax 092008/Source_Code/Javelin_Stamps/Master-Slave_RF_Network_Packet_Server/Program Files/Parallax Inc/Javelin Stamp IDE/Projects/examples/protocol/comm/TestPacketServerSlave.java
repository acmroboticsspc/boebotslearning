import Stamp.protocol.Comm.*;
import stamp.core.*;

/**
 * test PacketServer class.
 *
 * @version 1.0 1/23/2003
 * @author Bill Wong
 */

public class TestPacketServerSlave {
  public static void main() {
    PacketServer packetServer =
      new PacketServer ( PacketServer.noPorts
                                      // ports
                       , CPU.pin15    // input pin
                       , CPU.pin14    // output pin
                       , false        // half duplex
                       , true         // data invert
                       , Uart.speed9600 ) ;
    boolean sending = false ;

    StringBuffer buffer = new StringBuffer(100);

    packetServer.setReceiveBuffer(buffer);

    while(true) {
      if ( packetServer.receiveBufferFull()) {
        packetServer.setReceiveBuffer(null);

        System.out.print("Received <");
        System.out.print(buffer.toString());
        System.out.println(">");
        if ( packetServer.receivedBroadcast()) {
          packetServer.send(buffer);
        } else {
          packetServer.broadcast(buffer);
        }

        sending = true ;
      }

      if ( sending && packetServer.bufferSent()) {
        sending = false ;
        packetServer.setReceiveBuffer(buffer);
      }
    }
  }
}