package com.distkv.parser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import com.distkv.common.exception.DistkvException;

public class DistkvSqlErrorListener extends BaseErrorListener {
  private DistkvSqlErrorListener() {
  }

  public static DistkvSqlErrorListener INSTANCE = new DistkvSqlErrorListener();

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                          int line, int charPositionInLine, String msg, RecognitionException e) {

    String sourceName = recognizer.getInputStream().getSourceName();
    if (!sourceName.isEmpty()) {
      sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
    }
    // TODO(qwang): This exception should be refined.
    if (recognizer.getState() == 128) {
      throw new DistkvException("X010",
            sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    } else {
      throw new DistkvException("X020",
            sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
    }
  }
}
