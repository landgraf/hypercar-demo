FILESEXTRAPATHS:prepend := "${THISDIR}/linux/:"
SRC_URI:append = "file://defconfig"
COMPATIBLE_MACHINE =  "^(qemuarm-efi|qemuarm64-efi|qemux86|qemux86-64|qemuriscv64|qemuriscv32)$"