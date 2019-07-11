package org.dst.server.service;

public final class EchoExample {
  private EchoExample() {
  }

  public static void registerAllExtensions(
          com.google.protobuf.ExtensionRegistry registry) {
  }

  public interface EchoRequestOrBuilder
          extends com.google.protobuf.MessageOrBuilder {

    // required string message = 1;

    /**
     * <code>required string message = 1;</code>
     */
    boolean hasMessage();

    /**
     * <code>required string message = 1;</code>
     */
    String getMessage();

    /**
     * <code>required string message = 1;</code>
     */
    com.google.protobuf.ByteString
    getMessageBytes();
  }

  /**
   * Protobuf type {@code example_for_java.EchoRequest}
   */
  public static final class EchoRequest extends
          com.google.protobuf.GeneratedMessage
          implements EchoRequestOrBuilder {
    // Use EchoRequest.newBuilder() to construct.
    private EchoRequest(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }

    private EchoRequest(boolean noInit) {
      this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }

    private static final EchoRequest defaultInstance;

    public static EchoRequest getDefaultInstance() {
      return defaultInstance;
    }

    public EchoRequest getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private EchoRequest(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
              com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                      extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              message_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoRequest_descriptor;
    }

    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoRequest_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      org.dst.server.service.EchoExample.EchoRequest.class, org.dst.server.service.EchoExample.EchoRequest.Builder.class);
    }

    public static com.google.protobuf.Parser<EchoRequest> PARSER =
            new com.google.protobuf.AbstractParser<EchoRequest>() {
              public EchoRequest parsePartialFrom(
                      com.google.protobuf.CodedInputStream input,
                      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                      throws com.google.protobuf.InvalidProtocolBufferException {
                return new EchoRequest(input, extensionRegistry);
              }
            };

    @Override
    public com.google.protobuf.Parser<EchoRequest> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string message = 1;
    public static final int MESSAGE_FIELD_NUMBER = 1;
    private Object message_;

    /**
     * <code>required string message = 1;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }

    /**
     * <code>required string message = 1;</code>
     */
    public String getMessage() {
      Object ref = message_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
                (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          message_ = s;
        }
        return s;
      }
    }

    /**
     * <code>required string message = 1;</code>
     */
    public com.google.protobuf.ByteString
    getMessageBytes() {
      Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
                com.google.protobuf.ByteString.copyFromUtf8(
                        (String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      message_ = "";
    }

    private byte memoizedIsInitialized = -1;

    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasMessage()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getMessageBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
                .computeBytesSize(1, getMessageBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;

    @Override
    protected Object writeReplace()
            throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(java.io.InputStream input)
            throws java.io.IOException {
      return PARSER.parseFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
      return PARSER.parseFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoRequest parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(org.dst.server.service.EchoExample.EchoRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    @Override
    protected Builder newBuilderForType(
            BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code example_for_java.EchoRequest}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessage.Builder<Builder>
            implements org.dst.server.service.EchoExample.EchoRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoRequest_descriptor;
      }

      protected FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoRequest_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        org.dst.server.service.EchoExample.EchoRequest.class, org.dst.server.service.EchoExample.EchoRequest.Builder.class);
      }

      // Construct using org.dst.server.service.EchoExample.EchoRequest.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
              BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }

      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        message_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoRequest_descriptor;
      }

      public org.dst.server.service.EchoExample.EchoRequest getDefaultInstanceForType() {
        return org.dst.server.service.EchoExample.EchoRequest.getDefaultInstance();
      }

      public org.dst.server.service.EchoExample.EchoRequest build() {
        org.dst.server.service.EchoExample.EchoRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public org.dst.server.service.EchoExample.EchoRequest buildPartial() {
        org.dst.server.service.EchoExample.EchoRequest result = new org.dst.server.service.EchoExample.EchoRequest(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.message_ = message_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.dst.server.service.EchoExample.EchoRequest) {
          return mergeFrom((org.dst.server.service.EchoExample.EchoRequest) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.dst.server.service.EchoExample.EchoRequest other) {
        if (other == org.dst.server.service.EchoExample.EchoRequest.getDefaultInstance()) return this;
        if (other.hasMessage()) {
          bitField0_ |= 0x00000001;
          message_ = other.message_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasMessage()) {

          return false;
        }
        return true;
      }

      public Builder mergeFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws java.io.IOException {
        org.dst.server.service.EchoExample.EchoRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.dst.server.service.EchoExample.EchoRequest) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

      // required string message = 1;
      private Object message_ = "";

      /**
       * <code>required string message = 1;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }

      /**
       * <code>required string message = 1;</code>
       */
      public String getMessage() {
        Object ref = message_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref)
                  .toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }

      /**
       * <code>required string message = 1;</code>
       */
      public com.google.protobuf.ByteString
      getMessageBytes() {
        Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder setMessage(
              String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        message_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000001);
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder setMessageBytes(
              com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        message_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:example_for_java.EchoRequest)
    }

    static {
      defaultInstance = new EchoRequest(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:example_for_java.EchoRequest)
  }

  public interface EchoResponseOrBuilder
          extends com.google.protobuf.MessageOrBuilder {

    // required string message = 1;

    /**
     * <code>required string message = 1;</code>
     */
    boolean hasMessage();

    /**
     * <code>required string message = 1;</code>
     */
    String getMessage();

    /**
     * <code>required string message = 1;</code>
     */
    com.google.protobuf.ByteString
    getMessageBytes();
  }

  /**
   * Protobuf type {@code example_for_java.EchoResponse}
   */
  public static final class EchoResponse extends
          com.google.protobuf.GeneratedMessage
          implements EchoResponseOrBuilder {
    // Use EchoResponse.newBuilder() to construct.
    private EchoResponse(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }

    private EchoResponse(boolean noInit) {
      this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }

    private static final EchoResponse defaultInstance;

    public static EchoResponse getDefaultInstance() {
      return defaultInstance;
    }

    public EchoResponse getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }

    private EchoResponse(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
              com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                      extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              message_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
                e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }

    public static final com.google.protobuf.Descriptors.Descriptor
    getDescriptor() {
      return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoResponse_descriptor;
    }

    protected FieldAccessorTable
    internalGetFieldAccessorTable() {
      return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoResponse_fieldAccessorTable
              .ensureFieldAccessorsInitialized(
                      org.dst.server.service.EchoExample.EchoResponse.class, org.dst.server.service.EchoExample.EchoResponse.Builder.class);
    }

    public static com.google.protobuf.Parser<EchoResponse> PARSER =
            new com.google.protobuf.AbstractParser<EchoResponse>() {
              public EchoResponse parsePartialFrom(
                      com.google.protobuf.CodedInputStream input,
                      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                      throws com.google.protobuf.InvalidProtocolBufferException {
                return new EchoResponse(input, extensionRegistry);
              }
            };

    @Override
    public com.google.protobuf.Parser<EchoResponse> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // required string message = 1;
    public static final int MESSAGE_FIELD_NUMBER = 1;
    private Object message_;

    /**
     * <code>required string message = 1;</code>
     */
    public boolean hasMessage() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }

    /**
     * <code>required string message = 1;</code>
     */
    public String getMessage() {
      Object ref = message_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs =
                (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          message_ = s;
        }
        return s;
      }
    }

    /**
     * <code>required string message = 1;</code>
     */
    public com.google.protobuf.ByteString
    getMessageBytes() {
      Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
                com.google.protobuf.ByteString.copyFromUtf8(
                        (String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      message_ = "";
    }

    private byte memoizedIsInitialized = -1;

    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      if (!hasMessage()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
            throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getMessageBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;

    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
                .computeBytesSize(1, getMessageBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;

    @Override
    protected Object writeReplace()
            throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            com.google.protobuf.ByteString data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            com.google.protobuf.ByteString data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(byte[] data)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            byte[] data,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(java.io.InputStream input)
            throws java.io.IOException {
      return PARSER.parseFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseDelimitedFrom(java.io.InputStream input)
            throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseDelimitedFrom(
            java.io.InputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            com.google.protobuf.CodedInputStream input)
            throws java.io.IOException {
      return PARSER.parseFrom(input);
    }

    public static org.dst.server.service.EchoExample.EchoResponse parseFrom(
            com.google.protobuf.CodedInputStream input,
            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
            throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return Builder.create();
    }

    public Builder newBuilderForType() {
      return newBuilder();
    }

    public static Builder newBuilder(org.dst.server.service.EchoExample.EchoResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }

    public Builder toBuilder() {
      return newBuilder(this);
    }

    @Override
    protected Builder newBuilderForType(
            BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }

    /**
     * Protobuf type {@code example_for_java.EchoResponse}
     */
    public static final class Builder extends
            com.google.protobuf.GeneratedMessage.Builder<Builder>
            implements org.dst.server.service.EchoExample.EchoResponseOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoResponse_descriptor;
      }

      protected FieldAccessorTable
      internalGetFieldAccessorTable() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoResponse_fieldAccessorTable
                .ensureFieldAccessorsInitialized(
                        org.dst.server.service.EchoExample.EchoResponse.class, org.dst.server.service.EchoExample.EchoResponse.Builder.class);
      }

      // Construct using org.dst.server.service.EchoExample.EchoResponse.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
              BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }

      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }

      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        message_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
      getDescriptorForType() {
        return org.dst.server.service.EchoExample.internal_static_example_for_java_EchoResponse_descriptor;
      }

      public org.dst.server.service.EchoExample.EchoResponse getDefaultInstanceForType() {
        return org.dst.server.service.EchoExample.EchoResponse.getDefaultInstance();
      }

      public org.dst.server.service.EchoExample.EchoResponse build() {
        org.dst.server.service.EchoExample.EchoResponse result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public org.dst.server.service.EchoExample.EchoResponse buildPartial() {
        org.dst.server.service.EchoExample.EchoResponse result = new org.dst.server.service.EchoExample.EchoResponse(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.message_ = message_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof org.dst.server.service.EchoExample.EchoResponse) {
          return mergeFrom((org.dst.server.service.EchoExample.EchoResponse) other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(org.dst.server.service.EchoExample.EchoResponse other) {
        if (other == org.dst.server.service.EchoExample.EchoResponse.getDefaultInstance()) return this;
        if (other.hasMessage()) {
          bitField0_ |= 0x00000001;
          message_ = other.message_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        if (!hasMessage()) {

          return false;
        }
        return true;
      }

      public Builder mergeFrom(
              com.google.protobuf.CodedInputStream input,
              com.google.protobuf.ExtensionRegistryLite extensionRegistry)
              throws java.io.IOException {
        org.dst.server.service.EchoExample.EchoResponse parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (org.dst.server.service.EchoExample.EchoResponse) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int bitField0_;

      // required string message = 1;
      private Object message_ = "";

      /**
       * <code>required string message = 1;</code>
       */
      public boolean hasMessage() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }

      /**
       * <code>required string message = 1;</code>
       */
      public String getMessage() {
        Object ref = message_;
        if (!(ref instanceof String)) {
          String s = ((com.google.protobuf.ByteString) ref)
                  .toStringUtf8();
          message_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }

      /**
       * <code>required string message = 1;</code>
       */
      public com.google.protobuf.ByteString
      getMessageBytes() {
        Object ref = message_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b =
                  com.google.protobuf.ByteString.copyFromUtf8(
                          (String) ref);
          message_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder setMessage(
              String value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        message_ = value;
        onChanged();
        return this;
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder clearMessage() {
        bitField0_ = (bitField0_ & ~0x00000001);
        message_ = getDefaultInstance().getMessage();
        onChanged();
        return this;
      }

      /**
       * <code>required string message = 1;</code>
       */
      public Builder setMessageBytes(
              com.google.protobuf.ByteString value) {
        if (value == null) {
          throw new NullPointerException();
        }
        bitField0_ |= 0x00000001;
        message_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:example_for_java.EchoResponse)
    }

    static {
      defaultInstance = new EchoResponse(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:example_for_java.EchoResponse)
  }

  private static com.google.protobuf.Descriptors.Descriptor
          internal_static_example_for_java_EchoRequest_descriptor;
  private static
  com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internal_static_example_for_java_EchoRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
          internal_static_example_for_java_EchoResponse_descriptor;
  private static
  com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internal_static_example_for_java_EchoResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
  getDescriptor() {
    return descriptor;
  }

  private static com.google.protobuf.Descriptors.FileDescriptor
          descriptor;

  static {
    String[] descriptorData = {
            "\n\022test_prptoc2.proto\022\020example_for_java\"\036" +
                    "\n\013EchoRequest\022\017\n\007message\030\001 \002(\t\"\037\n\014EchoRe" +
                    "sponse\022\017\n\007message\030\001 \002(\tB(\n\026org.dst.serve" +
                    "r.serviceB\013EchoExample\200\001\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
            new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
              public com.google.protobuf.ExtensionRegistry assignDescriptors(
                      com.google.protobuf.Descriptors.FileDescriptor root) {
                descriptor = root;
                internal_static_example_for_java_EchoRequest_descriptor =
                        getDescriptor().getMessageTypes().get(0);
                internal_static_example_for_java_EchoRequest_fieldAccessorTable = new
                        com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                        internal_static_example_for_java_EchoRequest_descriptor,
                        new String[]{"Message",});
                internal_static_example_for_java_EchoResponse_descriptor =
                        getDescriptor().getMessageTypes().get(1);
                internal_static_example_for_java_EchoResponse_fieldAccessorTable = new
                        com.google.protobuf.GeneratedMessage.FieldAccessorTable(
                        internal_static_example_for_java_EchoResponse_descriptor,
                        new String[]{"Message",});
                return null;
              }
            };
    com.google.protobuf.Descriptors.FileDescriptor
            .internalBuildGeneratedFileFrom(descriptorData,
                    new com.google.protobuf.Descriptors.FileDescriptor[]{
                    }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
