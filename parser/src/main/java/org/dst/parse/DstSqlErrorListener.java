package org.dst.parse;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.dst.common.exception.DstException;

public class DstSqlErrorListener extends BaseErrorListener {
  private DstSqlErrorListener() {
  }

  public static DstSqlErrorListener INSTANCE = new DstSqlErrorListener();

  @Override
  public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                          int line, int charPositionInLine, String msg, RecognitionException e) {

    String sourceName = recognizer.getInputStream().getSourceName();
    if (!sourceName.isEmpty()) {
      sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
    }
    throw new DstException(sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
  }
}
