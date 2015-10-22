package bai4_rn.praktikum_01.command;

import java.util.List;

/**
 * Created by Andreas on 22.10.2015.
 */
public interface ClientResponse {
    String getName();
    void process();
//    List<ClientResponse> getPossibleSuccessors();
}
