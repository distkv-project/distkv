package com.distkv.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import com.distkv.common.exception.DistKVException;

public class DistKVSqlErrorListener extends BaseErrorListener {
  private DistKVSqlErrorListener() {
  }

  public static DistKVSqlErrorListener INSTANCE = new DistKVSqlErrorListener();

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                          int line, int charPositionInLine, String msg, RecognitionException e) {

    String sourceName = recognizer.getInputStream().getSourceName();
    if (!sourceName.isEmpty()) {
      sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
    }
    // TODO(qwang): This exception should be refined.
    if (recognizer.getState() == 128) {
      throw new DistKVException("X010",
            sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    } else {
      throw new DistKVException("X020",
            sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    }
  }
}
