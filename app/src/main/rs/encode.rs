#pragma version(1)
#pragma rs java_package_name(com.example.gabriele.steganography)

uchar* input_string;
int8_t char_counter; //counter for input_string characters
int8_t shift_counter; //counter for shifts in the input_string[char_counter] char
int32_t string_length; //lenght of input_string
uint8_t boolean; //flag for checking if the encoding is done or not

void init() {
    char_counter=0;
    boolean= 1;
    shift_counter=6;
}

uchar4 __attribute__((kernel)) encode(uchar4 in, uint32_t x, uint32_t y) {

  uchar4 out= in;

  int8_t i;

  for(i=0; i<3; i++){

    if(boolean){
      out[i]= out[i] & 0xFC;

      uchar temp_char= (input_string[char_counter] >> shift_counter) & 0x3;
      shift_counter -=2;

      if(shift_counter<0){
        shift_counter= 6;
        char_counter++;
      }

      out[i]= out[i] | temp_char;

      if(char_counter==string_length) boolean=0;
    }
    else break;

  }

  return out;
}