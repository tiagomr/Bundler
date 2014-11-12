Bundler
=======

Library to help with bundling complex classes in android.

Just include the aar file in your project as a library.

Example:

MyObject oneObject = new MyObject();


// To save the object
Bundle params = new Bundle();
Bundler.toBundle(params, "ObjectABC", oneObject);

// To fetch the object

MyObject anotherObject = Bundler.fromBundle(params, "ObjectABC", aux);

//Done :D
