do_deploy:append(){
    sed -i -e "s/BOOT_UART=0/BOOT_UART=1/" ${DEPLOYDIR}/${BOOTFILES_DIR_NAME}/bootcode.bin
}