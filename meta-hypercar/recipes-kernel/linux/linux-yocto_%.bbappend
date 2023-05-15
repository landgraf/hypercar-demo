FILESEXTRAPATHS:prepend := "${THISDIR}/linux/:"
SRC_URI:append = " file://xen.cfg \
	       	   file://systemd.cfg \
		   file://timers.cfg \
		   file://dmiid.cfg"

SRC_URI:append:raspberrypi4-64 = " file://rpi.cfg "
COMPATIBLE_MACHINE:append:qemuarm64-xen-efi =  "^qemuarm64-xen-efi$"
COMPATIBLE_MACHINE:append:qemuarm64-xen-uboot =  "^qemuarm64-xen-uboot$"

KERNEL_FEATURES:append = " \
    ${@bb.utils.contains('DISTRO_FEATURES', \
                         'xen', \
                         'features/xen-shmem.scc', \
                         '', d)} \
"