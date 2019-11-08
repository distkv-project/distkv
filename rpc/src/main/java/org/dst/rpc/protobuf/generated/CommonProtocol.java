// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common_pb.proto

package org.dst.rpc.protobuf.generated;

public final class CommonProtocol {
  private CommonProtocol() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code org.dst.rpc.protobuf.Status}
   */
  public enum Status
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>OK = 0;</code>
     */
    OK(0, 0),
    /**
     * <code>KEY_NOT_FOUND = 1;</code>
     */
    KEY_NOT_FOUND(1, 1),
    /**
     * <code>UNKNOWN_ERROR = 2;</code>
     */
    UNKNOWN_ERROR(2, 2),
    /**
     * <code>DICT_KEY_NOT_FOUND = 3;</code>
     *
     * <pre>
     * The key of the dict is not found.
     * </pre>
     */
    DICT_KEY_NOT_FOUND(3, 3),
    ;

    /**
     * <code>OK = 0;</code>
     */
    public static final int OK_VALUE = 0;
    /**
     * <code>KEY_NOT_FOUND = 1;</code>
     */
    public static final int KEY_NOT_FOUND_VALUE = 1;
    /**
     * <code>UNKNOWN_ERROR = 2;</code>
     */
    public static final int UNKNOWN_ERROR_VALUE = 2;
    /**
     * <code>DICT_KEY_NOT_FOUND = 3;</code>
     *
     * <pre>
     * The key of the dict is not found.
     * </pre>
     */
    public static final int DICT_KEY_NOT_FOUND_VALUE = 3;


    public final int getNumber() { return value; }

    public static Status valueOf(int value) {
      switch (value) {
        case 0: return OK;
        case 1: return KEY_NOT_FOUND;
        case 2: return UNKNOWN_ERROR;
        case 3: return DICT_KEY_NOT_FOUND;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Status>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<Status>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Status>() {
            public Status findValueByNumber(int number) {
              return Status.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return org.dst.rpc.protobuf.generated.CommonProtocol.getDescriptor().getEnumTypes().get(0);
    }

    private static final Status[] VALUES = values();

    public static Status valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private Status(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:org.dst.rpc.protobuf.Status)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017common_pb.proto\022\024org.dst.rpc.protobuf*" +
      "N\n\006Status\022\006\n\002OK\020\000\022\021\n\rKEY_NOT_FOUND\020\001\022\021\n\r" +
      "UNKNOWN_ERROR\020\002\022\026\n\022DICT_KEY_NOT_FOUND\020\003B" +
      "0\n\036org.dst.rpc.protobuf.generatedB\016Commo" +
      "nProtocol"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}