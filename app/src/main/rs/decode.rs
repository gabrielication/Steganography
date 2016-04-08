#pragma version(1)
#pragma rs java_package_name(com.example.gabriele.steganography)

uchar* output_string; //string to be pushed out
uchar4 ch; //this array contains four last 2 bits of every channel
uint32_t ind_string; //index of the last character in the string
uint32_t ind_char; //sort of lenght of the char for reconstructing
uint32_t len; //max memory available

void init() {
	ch = (uchar4) {(uchar) 0, (uchar) 0, (uchar) 0, (uchar) 0};
	ind_string= 0;
	ind_char=0;
	rsDebug("Called init", rsUptimeMillis());
}

void __attribute__((kernel)) decode(uchar4 in, uint32_t x, uint32_t y){
    uchar channel;
    uint32_t i;

    for(i=0; i<3; i++){
        if(ind_string==len) return;

        channel=(uchar) ((in[i]) & 0xFF); //takes channel r,g,b

        if(ind_char<4){ //check if char is completely reconstructed
            channel= channel & 0x3; //unset all bits except for two lsb
            ch[ind_char]= channel; //store them in the array;
            ind_char++; //increase the char counter
        }

        if(ind_char==4){ //if goes here, the char is reconstructed
            uchar tmp= 0x00; //8 bits set to 0
            tmp= tmp | ((ch[0]<<6) & 0xC0); //shift operations for reconstructing
            tmp= tmp | ((ch[1]<<4) & 0x30);
            tmp= tmp | ((ch[2]<<2) & 0x0C);
            tmp= tmp | (ch[3] & 0x03);

            output_string[ind_string]= tmp; //we save it to the output string
            ind_char=0;
            ind_string++; //remember to increase the string counter
        }
    }
}