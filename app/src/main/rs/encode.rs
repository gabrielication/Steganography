#pragma version(1)
#pragma rs java_package_name(com.example.gabriele.steganography.steganography)

uint32_t* bmp;

void __attribute__((kernel)) root(const uchar in, uint32_t x) {

    uint32_t a= ((bmp[x] >> 24) & 0xFF);
    uint32_t r= ((bmp[x] >> 16) & 0xFF);
    uint32_t g= ((bmp[x] >> 8) & 0xFF);
    uint32_t b= ((bmp[x]) & 0xFF);

    uint32_t pix= 0x00000000;
    pix= pix & (a << 24);
    //pix= pix & (r << 16);
    //pix= pix & (g << 8);
    pix= pix & (b);

    bmp[x]= x;

}