inherit native
DEPENDS = " xen ${MACHINE_DTB_PROVIDER} imagebuilder-native u-boot-mkimage-native"

do_bootscr() {
	${STAGING_DIR_NATIVE}/usr/libexec/uboot-script-gen -c imagebuilder.conf -d . -t virtio -o boot
        install -D -m 0644 boot.scr ${DEPLOYDIR}/boot.scr	    
}

do_bootscr[depends] = "imagebuilder-native:do_populate_sysroot \
u-boot-mkimage-native:do_populate_sysroot xen:do_populate_sysroot"
addtask bootscr after do_populate_sysroot before do_image