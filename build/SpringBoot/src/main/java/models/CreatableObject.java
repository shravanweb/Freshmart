package models;

import store.DatabaseObject;

public abstract class CreatableObject extends DatabaseObject {

  @Override
  public boolean _creatable() {
	return true;
  }
}
