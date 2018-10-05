{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "spring-web-socket-keycloak" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "spring-web-socket-keycloak.labels.selector" -}}
app: {{ template "spring-web-socket-keycloak.name" . }}
group: {{ .Values.labels.group }}
provider: {{ .Values.labels.provider }}
{{- end -}}

{{- define "spring-web-socket-keycloak.labels.stakater" -}}
{{ template "spring-web-socket-keycloak.labels.selector" . }}
version: "{{ .Values.labels.version }}"
{{- end -}}

{{- define "spring-web-socket-keycloak.labels.chart" -}}
chart: "{{ .Chart.Name }}-{{ .Chart.Version }}"
release: {{ .Release.Name | quote }}
heritage: {{ .Release.Service | quote }}
{{- end -}}
