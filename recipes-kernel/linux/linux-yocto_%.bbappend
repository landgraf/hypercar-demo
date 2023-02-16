FILESEXTRAPATHS:prepend := "${THISDIR}/linux/:"
SRC_URI:append = "file://xen.cfg"
COMPATIBLE_MACHINE =  "^qemuarm64-xen$"