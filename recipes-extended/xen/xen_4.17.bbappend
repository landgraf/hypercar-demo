FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRC_URI:append = "file://xen.cfg"

do_deploy:append(){
	install -d ${DEPLOYDIR}
	install -m 0644 ${WORKDIR}/xen.cfg ${DEPLOYDIR}
}

do_compile[nostamp] = "1"
