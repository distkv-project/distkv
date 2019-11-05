package org.dst.parse.execute;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.dst.common.exception.DstException;
import org.dst.rpc.protobuf.generated.SetProtocol;
import java.util.List;

public class DstSetExecute extends BaseExecute {


  public SetProtocol.PutRequest put() {
    this.requestType = "SetProtocol.PutRequest";
    SetProtocol.PutRequest.Builder request;
    request = SetProtocol.PutRequest.newBuilder();
    request.setKey(key);
    List<TerminalNode> node = (List<TerminalNode>) value;
    node.forEach(terminalNode -> request.addValues(terminalNode.getText()));
    return request.build();
  }

  public SetProtocol.GetRequest get() throws DstException {
    this.requestType = "SetProtocol.GetRequest";
    SetProtocol.GetRequest request =
        SetProtocol.GetRequest.newBuilder()
            .setKey(key)
            .build();
    return request;
  }

}
