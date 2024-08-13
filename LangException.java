class LangException extends Exception {
  String error;

  public LangException(String s) {
    this.error = s;
  }

  public String toString() {
    return error;
  }
}