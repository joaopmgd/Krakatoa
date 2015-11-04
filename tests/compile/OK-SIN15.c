#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_Test _class_Test;

struct _class_Test{
   Func *vt;
};

_class_Test *new_Test(void);


typedef enum {_enum_Test_fakeClone} _class_Test_methods;

char * _Test_fakeClone( _class_Test *this, char *_s ){
   return _s;
}

Func VTclass_Test[] = {
   ( void (*)() ) _Test_fakeClone
};

_class_Test *new_Test(){
   _class_Test *t;
   if ( (t = malloc(sizeof(_class_Test))) != NULL )
      t->vt = VTclass_Test;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   char __s[512];
   char *_s;
   _class_Test *_t;
   strcpy(_s,"Ola !!!");
_s = "Ola !!!";
   puts(_s);
   gets(__s);
   _s = malloc(strlen(__s)+1);
   strcpy(_s,__s);
   _t = new_Test();
   puts(( ( ( char * (*)(_class_Test *, char * ) ) _t->vt[_enum_Test_fakeClone] )( _t, "Dolly Parton") ));
   puts("barra   \\");
   puts("barra n  \n");
   puts("barra a  \a");
   puts("barra barra \\\\");
   puts("barra no final  \\ ");
}

Func VTclass_Program[] = {
   ( void (*)() ) _Program_run
};

_class_Program *new_Program(){
   _class_Program *t;
   if ( (t = malloc(sizeof(_class_Program))) != NULL )
      t->vt = VTclass_Program;
   return t;
}

int main() {
   _class_Program *program;
   program = new_Program();
   ( ( void (*)(_class_Program *) ) program->vt[0] )(program);
   return 0;
}