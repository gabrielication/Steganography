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


}