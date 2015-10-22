package bai4_rn.praktikum_01.command;

import java.io.IOException;
import java.util.List;

/**
 * Created by Andreas on 22.10.2015.
 */
public interface ClientResponse {
    String getName();
    ServerReply process() throws IOException;
//    List<ClientResponse> getPossibleSuccessors();
}
