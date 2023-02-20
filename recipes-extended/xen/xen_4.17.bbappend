FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = "file://xen.cfg"

DEPENDS:append = "imagebuilder-native"

do_deploy:append(){
	install -d ${DEPLOYDIR}
	install -m 0644 ${WORKDIR}/xen.cfg ${DEPLOYDIR}
	if [ -f ${B}/xen/xen ]; then
	   install -m 0644 ${B}/xen/xen ${DEPLOYDIR}/xen
	fi
}

do_compile[nostamp] = "1"
