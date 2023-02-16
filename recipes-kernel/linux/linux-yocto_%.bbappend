FILESEXTRAPATHS:prepend := "${THISDIR}/linux/:"
SRC_URI:append = " file://xen.cfg"
SRC_URI:append = " file://systemd.cfg"
COMPATIBLE_MACHINE =  "^qemuarm64-xen$"