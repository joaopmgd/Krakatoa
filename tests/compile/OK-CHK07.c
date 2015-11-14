#include <stdlib.h>
#include <string.h>
#include <stdio.h>

typedef int boolean;
#define true  1
#define false 0

typedef void (*Func)();

typedef struct _class_Person _class_Person;

struct _class_Person{
   Func *vt;
   char * _Person_course;
   int _Person_number;
   int _Person_age;
   char * _Person_name;
};

_class_Person *new_Person(void);


typedef enum {_enum_Person_getCourse, _enum_Person_setCourse, _enum_Person_getNumber, _enum_Person_setNumber, _enum_Person_init, _enum_Person_getName, _enum_Person_getAge, _enum_Person_print} _class_Person_methods;

char * _Person_getCourse( _class_Person *this ){
   return this->_Person_course;
}

void _Person_setCourse( _class_Person *this, char *_course ){
   strcpy(this->_Person_course,_course);
this->_Person_course = _course;
}

int _Person_getNumber( _class_Person *this ){
   return this->_Person_number;
}

void _Person_setNumber( _class_Person *this, int _number ){
   this->_Person_number = _number;
}

void _Person_init( _class_Person *this, char *_name, int _age ){
   strcpy(this->_Person_name,_name);
this->_Person_name = _name;
   this->_Person_age = _age;
}

char * _Person_getName( _class_Person *this ){
   return this->_Person_name;
}

int _Person_getAge( _class_Person *this ){
   return this->_Person_age;
}

void _Person_print( _class_Person *this ){
   puts("Name = ");
   puts(this->_Person_name);
   puts("Age = ");
   printf("%d",this->_Person_age);
}

Func VTclass_Person[] = {
   ( void (*)() ) _Person_getCourse, 
   ( void (*)() ) _Person_setCourse, 
   ( void (*)() ) _Person_getNumber, 
   ( void (*)() ) _Person_setNumber, 
   ( void (*)() ) _Person_init, 
   ( void (*)() ) _Person_getName, 
   ( void (*)() ) _Person_getAge, 
   ( void (*)() ) _Person_print
};

_class_Person *new_Person(){
   _class_Person *t;
   if ( (t = malloc(sizeof(_class_Person))) != NULL )
      t->vt = VTclass_Person;
   return t;
}

typedef struct _class_Group _class_Group;

struct _class_Group{
   Func *vt;
   _class_Person* _Group_first;
   _class_Person* _Group_second;
};

_class_Group *new_Group(void);


typedef enum {_enum_Group_set, _enum_Group_getFirst, _enum_Group_getSecond, _enum_Group_print} _class_Group_methods;

void _Group_set( _class_Group *this, _class_Person *_first, _class_Person *_second ){
   this->_Group_first = _first;
   this->_Group_second = _second;
}

_class_Person _Group_getFirst( _class_Group *this ){
   return *this->_Group_first;
}

_class_Person _Group_getSecond( _class_Group *this ){
   return *this->_Group_second;
}

void _Group_print( _class_Group *this ){
   puts("First: ");
   puts(( ( ( char * (*) (_class_Person *) ) this->_Group_first->vt[_enum_Person_getName] )( (_class_Person *) this ) ));
   puts("Second: ");
   puts(( ( ( char * (*) (_class_Person *) ) this->_Group_second->vt[_enum_Person_getName] )( (_class_Person *) this ) ));
}

Func VTclass_Group[] = {
   ( void (*)() ) _Group_set, 
   ( void (*)() ) _Group_getFirst, 
   ( void (*)() ) _Group_getSecond, 
   ( void (*)() ) _Group_print
};

_class_Group *new_Group(){
   _class_Group *t;
   if ( (t = malloc(sizeof(_class_Group))) != NULL )
      t->vt = VTclass_Group;
   return t;
}

typedef struct _class_University _class_University;

struct _class_University{
   Func *vt;
   char * _University_name;
   int _University_numberOfStudents;
   char * _University_city;
};

_class_University *new_University(void);


typedef enum {_enum_University_init, _enum_University_print} _class_University_methods;

void _University_init( _class_University *this, char *_name, char *_city, int _numberOfStudents ){
   strcpy(this->_University_name,_name);
this->_University_name = _name;
   strcpy(this->_University_city,_city);
this->_University_city = _city;
   this->_University_numberOfStudents = _numberOfStudents;
}

void _University_print( _class_University *this ){
   puts(this->_University_name);
   puts(this->_University_city);
   printf("%d",this->_University_numberOfStudents);
}

Func VTclass_University[] = {
   ( void (*)() ) _University_init, 
   ( void (*)() ) _University_print
};

_class_University *new_University(){
   _class_University *t;
   if ( (t = malloc(sizeof(_class_University))) != NULL )
      t->vt = VTclass_University;
   return t;
}

typedef struct _class_Program _class_Program;

struct _class_Program{
   Func *vt;
};

_class_Program *new_Program(void);


typedef enum {_enum_Program_run} _class_Program_methods;

void _Program_run( _class_Program *this ){
   _class_University *_s;
   _class_Person *_joao;
   _class_Person *_maria;
   _class_Group *_g;
   _s = new_University();
   ( ( ( void (*)(_class_University *, char *, char *, int ) ) _s->vt[_enum_University_init] )( _s, "UFSCar", "Sao Carlos", 7000) );
   ( ( ( void (*)(_class_University * ) ) _s->vt[_enum_University_print] )( _s) );
   _joao = new_Person();
   ( ( ( void (*)(_class_Person *, char *, int ) ) _joao->vt[_enum_Person_init] )( _joao, "Joao", 21) );
   ( ( ( void (*)(_class_Person *, char * ) ) _joao->vt[_enum_Person_setCourse] )( _joao, "EnC") );
   ( ( ( void (*)(_class_Person *, int ) ) _joao->vt[_enum_Person_setNumber] )( _joao, 6729) );
   _maria = new_Person();
   ( ( ( void (*)(_class_Person *, char *, int ) ) _maria->vt[_enum_Person_init] )( _maria, "Maria", 20) );
   ( ( ( void (*)(_class_Person *, char * ) ) _maria->vt[_enum_Person_setCourse] )( _maria, "Fisioterapia") );
   ( ( ( void (*)(_class_Person *, int ) ) _maria->vt[_enum_Person_setNumber] )( _maria, 8607) );
   ( ( ( void (*)(_class_Person * ) ) _joao->vt[_enum_Person_print] )( _joao) );
   ( ( ( void (*)(_class_Person * ) ) _maria->vt[_enum_Person_print] )( _maria) );
   _g = new_Group();
   ( ( ( void (*)(_class_Group *, _class_Person*, _class_Person* ) ) _g->vt[_enum_Group_set] )( _g, _joao, _maria) );
   ( ( ( void (*)(_class_Group * ) ) _g->vt[_enum_Group_print] )( _g) );
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