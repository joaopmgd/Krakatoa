#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   char *_r;
   char *_s;
   strcpy(_r,"Ola");
_r = "Ola";
   strcpy(_s,"Tudo bem?");
_s = "Tudo bem?";
   if ((((((((((_r == _s   ) && (_r != _s   )) && (_r == NULL   )) && (NULL == _r   )) && (_r == "Ola"   )) && ("Ola" == _r   )) && (_r != NULL   )) && (_r != "Ola"   )) && ("Ola" != _r   )) && ("null" == NULL   ))
      puts("impossivel");
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