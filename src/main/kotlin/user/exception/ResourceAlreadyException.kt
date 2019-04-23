package user.exception

import net.bytebuddy.implementation.bytecode.Throw

class ResourceAlreadyException(val fieldName: String, val resourceName: String) :
	Throwable(String.format("%s is already %s", resourceName, fieldName))