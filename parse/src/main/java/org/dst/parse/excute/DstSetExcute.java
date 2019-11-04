package org.dst.parse.excute;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.dst.common.exception.DstException;
import org.dst.common.exception.KeyNotFoundException;
import org.dst.parse.util.CodeUtils;
import org.dst.rpc.protobuf.generated.CommonProtocol;
import org.dst.rpc.protobuf.generated.SetProtocol;
import org.dst.rpc.service.DstSetService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class DstSetExcute extends  BaseExcute {

    private DstSetService service;

    public  SetProtocol.PutRequest put() {
        SetProtocol.PutRequest.Builder request = SetProtocol.PutRequest.newBuilder();
        request.setKey(key);
        List<TerminalNode> node=(List<TerminalNode>) value;
        node.forEach(terminalNode -> request.addValues(terminalNode.getText()));
        return request.build();
      /*  SetProtocol.PutResponse response = service.put(request.build());

        if (response.getStatus() != CommonProtocol.Status.OK) {
            throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
        }*/
    }

    public SetProtocol.GetRequest get() throws DstException {
        SetProtocol.GetRequest request =
                SetProtocol.GetRequest.newBuilder()
                        .setKey(key)
                        .build();

       /* SetProtocol.GetResponse response = service.get(request);
        if (response.getStatus() == CommonProtocol.Status.KEY_NOT_FOUND) {
            throw new KeyNotFoundException(key);
        } else if (response.getStatus() != CommonProtocol.Status.OK) {
            throw new DstException(String.format("Error code is %d", response.getStatus().getNumber()));
        }

        Set<String> set = new HashSet<>(response.getValuesList());*/
        return request;
    }

}
